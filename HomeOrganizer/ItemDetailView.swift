import SwiftUI

struct ItemDetailView: View {
    @State private var needsToBuy: Bool
    var item: Item
    var profile: Profile
    var category: Category
    @ObservedObject var appLogic: AppLogic
    @State private var showingEditItem = false

    init(item: Item, profile: Profile, category: Category, appLogic: AppLogic) {
        self.item = item
        self._needsToBuy = State(initialValue: item.needsToBuy)
        self.profile = profile
        self.category = category
        self.appLogic = appLogic
    }

    var body: some View {
        VStack {
            if let customImage = item.customImage {
                Image(uiImage: customImage)
                    .resizable()
                    .frame(width: 100, height: 100)
            }
            Text(item.name)
                .font(.largeTitle)
                .padding()
            Text("Price: $\(item.price, specifier: "%.2f")")
            Text("Link: \(item.link)")
            Text("Store: \(item.storeTag)")
            Toggle("Needs to Buy", isOn: $needsToBuy)
                .padding()
                .onChange(of: needsToBuy) { value in
                    saveChanges()
                }
        }
        .padding()
        .navigationTitle(item.name)
        .toolbar {
            ToolbarItem(placement: .navigationBarTrailing) {
                Button(action: {
                    showingEditItem = true
                }) {
                    Text("Edit")
                }
                .sheet(isPresented: $showingEditItem) {
                    EditItemView(item: item, profile: profile, category: category, appLogic: appLogic)
                }
            }
        }
    }

    private func saveChanges() {
        let newItem = Item(name: item.name, price: item.price, link: item.link, customImage: item.customImage, needsToBuy: needsToBuy, storeTag: item.storeTag)
        appLogic.editItem(in: category.name, of: profile.name, oldItemName: item.name, newItem: newItem)
    }
}

struct ItemDetailView_Previews: PreviewProvider {
    static var previews: some View {
        let appLogic = AppLogic()
        let profile = Profile(name: "House", customImage: nil)
        let category = Category(name: "Cleaning", customImage: nil)
        let item = Item(name: "Dish Soap", price: 3.99, link: "https://example.com", customImage: nil, needsToBuy: false, storeTag: "Walmart")
        category.addItem(item)
        profile.addCategory(category)
        appLogic.addProfile(profile)
        return ItemDetailView(item: item, profile: profile, category: category, appLogic: appLogic)
    }
}
