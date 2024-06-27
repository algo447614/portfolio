import SwiftUI

struct EditProfileView: View {
    @Environment(\.presentationMode) var presentationMode
    @State private var name: String
    @State private var customImage: UIImage?
    @State private var showingImagePicker = false
    var profile: Profile
    var appLogic: AppLogic

    init(profile: Profile, appLogic: AppLogic) {
        self.profile = profile
        self.appLogic = appLogic
        _name = State(initialValue: profile.name)
        _customImage = State(initialValue: profile.customImage)
    }

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
                    saveChanges()
                    presentationMode.wrappedValue.dismiss()
                }
                .disabled(name.isEmpty)
            }
            .navigationTitle("Edit Profile")
        }
    }

    private func saveChanges() {
        _ = Profile(name: name, customImage: customImage)
        appLogic.editProfile(oldName: profile.name, newName: name, newCustomImage: customImage)
    }
}

struct EditProfileView_Previews: PreviewProvider {
    static var previews: some View {
        let appLogic = AppLogic()
        let profile = Profile(name: "House", customImage: nil)
        return EditProfileView(profile: profile, appLogic: appLogic)
    }
}
