import Foundation
import UIKit

class Item: Identifiable {
    var id = UUID()
    var name: String
    var price: Double
    var link: String
    var customImage: UIImage?
    var needsToBuy: Bool
    var storeTag: String
    
    init(name: String, price: Double, link: String, customImage: UIImage?, needsToBuy: Bool, storeTag: String) {
        self.name = name
        self.price = price
        self.link = link
        self.customImage = customImage
        self.needsToBuy = needsToBuy
        self.storeTag = storeTag
    }
}

class Category: Identifiable {
    var id = UUID()
    var name: String
    var customImage: UIImage?
    var items: [Item]
    
    init(name: String, customImage: UIImage?) {
        self.name = name
        self.customImage = customImage
        self.items = []
    }
    
    func addItem(_ item: Item) {
        items.append(item)
    }
    
    func removeItem(named name: String) {
        items.removeAll { $0.name == name }
    }
    
    func editItem(oldName: String, newItem: Item) {
        if let index = items.firstIndex(where: { $0.name == oldName }) {
            items[index] = newItem
        }
    }
}

class Profile: Identifiable {
    var id = UUID()
    var name: String
    var customImage: UIImage?
    var categories: [Category]
    
    init(name: String, customImage: UIImage?) {
        self.name = name
        self.customImage = customImage
        self.categories = []
    }
    
    func addCategory(_ category: Category) {
        categories.append(category)
    }
    
    func removeCategory(named name: String) {
        categories.removeAll { $0.name == name }
    }
    
    func editCategory(oldName: String, newName: String, newCustomImage: UIImage?) {
        if let category = categories.first(where: { $0.name == oldName }) {
            category.name = newName
            category.customImage = newCustomImage
        }
    }
}

class AppLogic: ObservableObject {
    @Published var profiles: [Profile]
    
    init() {
        profiles = []
    }
    
    func addProfile(_ profile: Profile) {
        profiles.append(profile)
    }
    
    func getProfile(named name: String) -> Profile? {
        return profiles.first { $0.name == name }
    }
    
    func editProfile(oldName: String, newName: String, newCustomImage: UIImage?) {
        if let profile = getProfile(named: oldName) {
            profile.name = newName
            profile.customImage = newCustomImage
        }
    }
    
    func deleteProfile(named name: String) {
        profiles.removeAll { $0.name == name }
    }
    
    func addCategory(to profileName: String, category: Category) {
        if let profile = getProfile(named: profileName) {
            profile.addCategory(category)
        }
    }
    
    func deleteCategory(from profileName: String, categoryName: String) {
        if let profile = getProfile(named: profileName) {
            profile.removeCategory(named: categoryName)
        }
    }
    
    func editCategory(in profileName: String, oldCategoryName: String, newCategoryName: String, newCustomImage: UIImage?) {
        if let profile = getProfile(named: profileName) {
            if let category = profile.categories.first(where: { $0.name == oldCategoryName }) {
                category.name = newCategoryName
                category.customImage = newCustomImage
            }
        }
    }
    
    func addItem(to categoryName: String, in profileName: String, item: Item) {
        if let profile = getProfile(named: profileName) {
            if let category = profile.categories.first(where: { $0.name == categoryName }) {
                category.addItem(item)
            }
        }
    }
    
    func deleteItem(from categoryName: String, in profileName: String, itemName: String) {
        if let profile = getProfile(named: profileName) {
            if let category = profile.categories.first(where: { $0.name == categoryName }) {
                category.removeItem(named: itemName)
            }
        }
    }
    
    func editItem(in categoryName: String, of profileName: String, oldItemName: String, newItem: Item) {
        if let profile = getProfile(named: profileName) {
            if let category = profile.categories.first(where: { $0.name == categoryName }) {
                category.editItem(oldName: oldItemName, newItem: newItem)
            }
        }
    }
    
    func listItems(in categoryName: String, of profileName: String) -> [Item]? {
        if let profile = getProfile(named: profileName) {
            if let category = profile.categories.first(where: { $0.name == categoryName }) {
                return category.items
            }
        }
        return nil
    }
}
