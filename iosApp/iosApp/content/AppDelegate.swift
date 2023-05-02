//
//  AppDelegate.swift
//  iosApp
//
//  Created by Lucy on 02/05/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import FirebaseCore
import shared
import UIKit

class AppDelegate: NSObject, UIApplicationDelegate {
    
    let rootHolder: RootHolder = RootHolder(repostitory: TodoRepositoryHelper().getTodoRepository())
    
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        return true
    }
}
