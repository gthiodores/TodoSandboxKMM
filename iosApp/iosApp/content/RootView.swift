//
//  RootView.swift
//  iosApp
//
//  Created by Lucy on 02/05/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct RootView: View {
    private let root: RootComponent

    @ObservedObject
    private var childStack: ObservableValue<ChildStack<AnyObject, RootComponentChild>>

    private var activeChild: RootComponentChild { childStack.value.active.instance }

    init(_ root: RootComponent) {
        self.root = root
        childStack = ObservableValue(root.childStack)
    }

    var body: some View {
        switch activeChild {
        case let child as RootComponentChild.Todo: TodoView(toAbout: root.navigateToAbout, componentWrapper: TodoComponentWrapper(component: child.component))
        case _ as RootComponentChild.About: AboutView(onBack: root.navigateBackFromAbout)
        default: EmptyView()
        }
    }
}
