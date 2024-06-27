import SwiftUI

struct EditItemView: View {
    @Environment(\.presentationMode) var presentationMode
    @State private var name: String
    @State private var price: String
    @State private var link: String
    @State private var customImage: UIImage?
    @State private var storeTag: String
    @State private var needsToBuy: Bool
    @State private var showingImagePicker = false
    var item: Item
    var profile: Profile
    var category: Category
    var appLogic: AppLogic

    init(item: Item, profile: Profile, category: Category, appLogic: AppLogic) {
        self.item = item
        self.profile = profile
        self.category = category
        self.appLogic = appLogic
        _name = State(initialValue: item.name)
        _price = State(initialValue: "\(item.price)")
        _link = State(initialValue: item.link)
        _customImage = State(initialValue: item.customImage)
        _storeTag = State(initialValue: item.storeTag)
        _needsToBuy = State(initialValue: item.needsToBuy)
    }

    var body: some View {
        NavigationView {
            Form {
                TextField("Item Name", text: $name)
                TextField("Price", text: $price)
                    .keyboardType(.decimalPad)
                TextField("Link", text: $link)
                TextField("Store Tag", text: $storeTag)
                Toggle("Needs to Buy", isOn: $needsToBuy)

                Button("Upload Image") {
                    showingImagePicker = true
                }
                .sheet(isPresented: $showingImagePicker) {
                    ImagePicker(image: $customImage)
                }

                if let customImage = customImage {
                    Image(uiImage: customImage)
                        .resizable()
                        .frame(width: 50, height: 50)
                        .padding()
                }

                Button("Save") {
                    saveChanges()
                    presentationMode.wrappedValue.dismiss()
                }
                .disabled(name.isEmpty || price.isEmpty)
            }
            .navigationTitle("Edit Item")
        }
    }

    private func saveChanges() {
        if let priceValue = Double(price) {
            let newItem = Item(name: name, price: priceValue, link: link, customImage: customImage, needsToBuy: needsToBuy, storeTag: storeTag)
            appLogic.editItem(in: category.name, of: profile.name, oldItemName: item.name, newItem: newItem)
        }
    }
}

struct EditItemView_Previews: PreviewProvider {
    static var previews: some View {
        let appLogic = AppLogic()
        let profile = Profile(name: "House", customImage: nil)
        let category = Category(name: "Cleaning", customImage: nil)
        let item = Item(name: "Dish Soap", price: 3.99, link: "https://example.com", customImage: nil, needsToBuy: false, storeTag: "Walmart")
        return EditItemView(item: item, profile: profile, category: category, appLogic: appLogic)
    }
}
