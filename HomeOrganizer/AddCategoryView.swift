import SwiftUI

struct AddCategoryView: View {
    @Environment(\.presentationMode) var presentationMode
    @State private var name: String = ""
    @State private var customImage: UIImage? = nil
    @State private var showingImagePicker = false
    var profile: Profile
    var appLogic: AppLogic

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
                    let category = Category(name: name, customImage: customImage)
                    appLogic.addCategory(to: profile.name, category: category)
                    presentationMode.wrappedValue.dismiss()
                }
                .disabled(name.isEmpty)
            }
            .navigationTitle("Add Category")
        }
    }
}

struct AddCategoryView_Previews: PreviewProvider {
    static var previews: some View {
        let appLogic = AppLogic()
        let profile = Profile(name: "House", customImage: nil)
        return AddCategoryView(profile: profile, appLogic: appLogic)
    }
}
