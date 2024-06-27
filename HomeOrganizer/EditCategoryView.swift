import SwiftUI

struct EditCategoryView: View {
    @Environment(\.presentationMode) var presentationMode
    @State private var name: String
    @State private var customImage: UIImage?
    @State private var showingImagePicker = false
    var category: Category
    var profile: Profile
    var appLogic: AppLogic

    init(category: Category, profile: Profile, appLogic: AppLogic) {
        self.category = category
        self.profile = profile
        self.appLogic = appLogic
        _name = State(initialValue: category.name)
        _customImage = State(initialValue: category.customImage)
    }

    var body: some View {
        NavigationView {
            Form {
                TextField("Category Name", text: $name)

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
                .disabled(name.isEmpty)
            }
            .navigationTitle("Edit Category")
        }
    }

    private func saveChanges() {
        appLogic.editCategory(in: profile.name, oldCategoryName: category.name, newCategoryName: name, newCustomImage: customImage)
    }
}

struct EditCategoryView_Previews: PreviewProvider {
    static var previews: some View {
        let appLogic = AppLogic()
        let profile = Profile(name: "House", customImage: nil)
        let category = Category(name: "Cleaning", customImage: nil)
        return EditCategoryView(category: category, profile: profile, appLogic: appLogic)
    }
}
