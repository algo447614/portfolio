import SwiftUI

struct MainView: View {
    @ObservedObject var appLogic = AppLogic()
    @State private var showingAllItems = false
    @State private var showingShoppingList = false

    let columns = [
        GridItem(.flexible()),
        GridItem(.flexible())
    ]

    var body: some View {
        NavigationView {
            VStack {
                ScrollView {
                    LazyVGrid(columns: columns, spacing: 20) {
                        ForEach(appLogic.profiles, id: \.id) { profile in
                            NavigationLink(destination: ProfileDetailView(profile: profile, appLogic: appLogic)) {
                                VStack {
                                    if let customImage = profile.customImage {
                                        Image(uiImage: customImage)
                                            .resizable()
                                            .aspectRatio(contentMode: .fill)
                                            .frame(width: 100, height: 100)
                                            .clipShape(RoundedRectangle(cornerRadius: 10))
                                    } else {
                                        Image(systemName: "photo")
                                            .resizable()
                                            .aspectRatio(contentMode: .fill)
                                            .frame(width: 100, height: 100)
                                            .clipShape(RoundedRectangle(cornerRadius: 10))
                                    }
                                    Text(profile.name)
                                        .font(.headline)
                                        .frame(maxWidth: .infinity)
                                }
                                .padding()
                                .background(Color(UIColor.secondarySystemBackground))
                                .clipShape(RoundedRectangle(cornerRadius: 10))
                                .shadow(radius: 5)
                            }
                        }
                    }
                    .padding()
                }
                .navigationTitle("Profiles")
                .toolbar {
                    ToolbarItem(placement: .navigationBarTrailing) {
                        NavigationLink(destination: AddProfileView(appLogic: appLogic)) {
                            Image(systemName: "plus")
                        }
                    }
                }

                Button(action: {
                    showingAllItems = true
                }) {
                    Text("View All Items")
                        .padding()
                        .background(Color.blue)
                        .foregroundColor(.white)
                        .cornerRadius(10)
                }
                .sheet(isPresented: $showingAllItems) {
                    AllItemsView(appLogic: appLogic)
                }

                Button(action: {
                    showingShoppingList = true
                }) {
                    Text("View Shopping List")
                        .padding()
                        .background(Color.green)
                        .foregroundColor(.white)
                        .cornerRadius(10)
                }
                .sheet(isPresented: $showingShoppingList) {
                    ShoppingListView(appLogic: appLogic)
                }
            }
        }
    }
}

struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}
