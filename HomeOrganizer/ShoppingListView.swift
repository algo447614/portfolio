import SwiftUI

struct ShoppingListView: View {
    @ObservedObject var appLogic: AppLogic

    var body: some View {
        NavigationView {
            List {
                ForEach(appLogic.profiles, id: \.id) { profile in
                    Section(header: Text(profile.name).font(.title2)) {
                        ForEach(profile.categories, id: \.id) { category in
                            Section(header: Text(category.name).font(.headline)) {
                                let itemsGroupedByStore = Dictionary(grouping: category.items.filter { $0.needsToBuy }, by: { $0.storeTag })
                                ForEach(itemsGroupedByStore.keys.sorted(), id: \.self) { store in
                                    Section(header: Text(store).padding(.leading, 15)) {
                                        ForEach(itemsGroupedByStore[store] ?? [], id: \.id) { item in
                                            HStack {
                                                if let customImage = item.customImage {
                                                    Image(uiImage: customImage)
                                                        .resizable()
                                                        .frame(width: 30, height: 30)
                                                }
                                                VStack(alignment: .leading) {
                                                    Text(item.name)
                                                        .contextMenu {
                                                            Button(action: {
                                                                if let url = URL(string: item.link) {
                                                                    UIApplication.shared.open(url)
                                                                }
                                                            }) {
                                                                Text("Open Link")
                                                                Image(systemName: "link")
                                                            }
                                                        }
                                                    Text("$\(item.price, specifier: "%.2f")")
                                                        .font(.subheadline)
                                                }
                                                Spacer()
                                                CheckBoxView(item: item, profile: profile, category: category, appLogic: appLogic)
                                            }
                                            .contentShape(Rectangle()) // Make the entire row clickable for the checkbox
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Section {
                    HStack {
                        Spacer()
                        Text("Total: $\(totalPrice(), specifier: "%.2f")")
                            .font(.headline)
                    }
                }
            }
            .navigationTitle("Shopping List")
        }
    }

    private func totalPrice() -> Double {
        var total: Double = 0.0
        for profile in appLogic.profiles {
            for category in profile.categories {
                for item in category.items where item.needsToBuy {
                    total += item.price
                }
            }
        }
        return total
    }
}

struct ShoppingListView_Previews: PreviewProvider {
    static var previews: some View {
        let appLogic = AppLogic()
        let profile = Profile(name: "House", customImage: nil)
        let category = Category(name: "Cleaning", customImage: nil)
        let item = Item(name: "Dish Soap", price: 3.99, link: "https://example.com", customImage: nil, needsToBuy: true, storeTag: "Walmart")
        category.addItem(item)
        profile.addCategory(category)
        appLogic.addProfile(profile)
        return ShoppingListView(appLogic: appLogic)
    }
}
