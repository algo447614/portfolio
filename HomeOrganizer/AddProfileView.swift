import SwiftUI

struct AddProfileView: View {
    @Environment(\.presentationMode) var presentationMode
    @State private var name: String = ""
    @State private var customImage: UIImage? = nil
    @State private var showingImagePicker = false
    var appLogic: AppLogic

    var body: some View {
        NavigationView {
            Form {
                TextField("Profile Name", text: $name)

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
                    let profile = Profile(name: name, customImage: customImage)
                    appLogic.addProfile(profile)
                    presentationMode.wrappedValue.dismiss()
                }
                .disabled(name.isEmpty)
            }
            .navigationTitle("Add Profile")
        }
    }
}

struct AddProfileView_Previews: PreviewProvider {
    static var previews: some View {
        AddProfileView(appLogic: AppLogic())
    }
}
