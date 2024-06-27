import SwiftUI

struct AddItemView: View {
    @Environment(\.presentationMode) var presentationMode
    @State private var name: String = ""
    @State private var price: String = ""
    @State private var link: String = ""
    @State private var customImage: UIImage? = nil
    @State private var showingImagePicker = false
    @State private var storeTag: String = ""
    @State private var needsToBuy: Bool = false
    var category: Category
    var profile: Profile
    var appLogic: AppLogic

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
                    if let priceValue = Double(price) {
                        let item = Item(name: name, price: priceValue, link: link, customImage: customImage, needsToBuy: needsToBuy, storeTag: storeTag)
                        appLogic.addItem(to: category.name, in: profile.name, item: item)
                        presentationMode.wrappedValue.dismiss()
                    }
                }
                .disabled(name.isEmpty || price.isEmpty)
            }
            .navigationTitle("Add Item")
        }
    }
}

struct AddItemView_Previews: PreviewProvider {
    static var previews: some View {
        let appLogic = AppLogic()
        let profile = Profile(name: "House", customImage: nil)
        let category = Category(name: "Cleaning", customImage: nil)
        return AddItemView(category: category, profile: profile, appLogic: appLogic)
    }
}
