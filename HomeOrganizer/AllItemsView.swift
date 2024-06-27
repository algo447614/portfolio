import SwiftUI

struct AllItemsView: View {
    @ObservedObject var appLogic: AppLogic

    var body: some View {
        NavigationView {
            List {
                ForEach(appLogic.profiles, id: \.id) { profile in
                    Section(header: Text(profile.name).font(.title2)) {
                        ForEach(profile.categories, id: \.id) { category in
                            Section(header: Text(category.name).font(.headline)) {
                                let itemsGroupedByStore = Dictionary(grouping: category.items, by: { $0.storeTag })
                                ForEach(itemsGroupedByStore.keys.sorted(), id: \.self) { store in
                                    Section(header: Text(store).padding(.leading, 15)) {
                                        ForEach(itemsGroupedByStore[store] ?? [], id: \.id) { item in
                                            HStack {
                                                if let customImage = item.customImage {
                                                    Image(uiImage: customImage)
                                                        .resizable()
                                                        .frame(width: 30, height: 30)
                                                }
                                                Text(item.name)
                                                Spacer()
                                                Text("$\(item.price, specifier: "%.2f")")
                                                if item.needsToBuy {
                                                    Rectangle()
                                                        .fill(Color.red)
                                                        .frame(width: 10, height: 10)
                                                        .cornerRadius(2)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            .navigationTitle("All Items")
        }
    }
}

struct AllItemsView_Previews: PreviewProvider {
    static var previews: some View {
        let appLogic = AppLogic()
        let profile = Profile(name: "House", customImage: nil)
        let category = Category(name: "Cleaning", customImage: nil)
        let item = Item(name: "Dish Soap", price: 3.99, link: "https://example.com", customImage: nil, needsToBuy: false, storeTag: "Walmart")
        category.addItem(item)
        profile.addCategory(category)
        appLogic.addProfile(profile)
        return AllItemsView(appLogic: appLogic)
    }
}
