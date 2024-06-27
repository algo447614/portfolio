import SwiftUI

struct CategoryView: View {
    var category: Category
    var profile: Profile
    @ObservedObject var appLogic: AppLogic
    @State private var showingAddItem = false
    @State private var showingEditCategory = false

    var body: some View {
        VStack {
            List {
                ForEach(category.items, id: \.id) { item in
                    NavigationLink(destination: ItemDetailView(item: item, profile: profile, category: category, appLogic: appLogic)) {
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

            Button(action: {
                showingAddItem = true
            }) {
                Text("Add Item")
                    .padding()
                    .background(Color.blue)
                    .foregroundColor(.white)
                    .cornerRadius(10)
            }
            .sheet(isPresented: $showingAddItem) {
                AddItemView(category: category, profile: profile, appLogic: appLogic)
            }
        }
        .navigationTitle(category.name)
        .toolbar {
            ToolbarItem(placement: .navigationBarTrailing) {
                Button(action: {
                    showingEditCategory = true
                }) {
                    Text("Edit")
                }
                .sheet(isPresented: $showingEditCategory) {
                    EditCategoryView(category: category, profile: profile, appLogic: appLogic)
                }
            }
        }
    }
}

struct CategoryView_Previews: PreviewProvider {
    static var previews: some View {
        let appLogic = AppLogic()
        let profile = Profile(name: "House", customImage: nil)
        let category = Category(name: "Cleaning", customImage: nil)
        return CategoryView(category: category, profile: profile, appLogic: appLogic)
    }
}
