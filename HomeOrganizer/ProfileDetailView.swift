import SwiftUI

struct ProfileDetailView: View {
    var profile: Profile
    @ObservedObject var appLogic: AppLogic
    @State private var showingAddCategory = false
    @State private var showingEditProfile = false

    var body: some View {
        VStack {
            List {
                ForEach(profile.categories, id: \.id) { category in
                    NavigationLink(destination: CategoryView(category: category, profile: profile, appLogic: appLogic)) {
                        HStack {
                            if let customImage = category.customImage {
                                Image(uiImage: customImage)
                                    .resizable()
                                    .frame(width: 30, height: 30)
                            }
                            Text(category.name)
                        }
                    }
                }
            }

            Button(action: {
                showingAddCategory = true
            }) {
                Text("Add Category")
                    .padding()
                    .background(Color.blue)
                    .foregroundColor(.white)
                    .cornerRadius(10)
            }
            .sheet(isPresented: $showingAddCategory) {
                AddCategoryView(profile: profile, appLogic: appLogic)
            }
        }
        .navigationTitle(profile.name)
        .toolbar {
            ToolbarItem(placement: .navigationBarTrailing) {
                Button(action: {
                    showingEditProfile = true
                }) {
                    Text("Edit")
                }
                .sheet(isPresented: $showingEditProfile) {
                    EditProfileView(profile: profile, appLogic: appLogic)
                }
            }
        }
    }
}

struct ProfileDetailView_Previews: PreviewProvider {
    static var previews: some View {
        let appLogic = AppLogic()
        let profile = Profile(name: "House", customImage: nil)
        return ProfileDetailView(profile: profile, appLogic: appLogic)
    }
}
