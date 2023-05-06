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
        ZStack {
            switch activeChild {
            case let child as RootComponentChild.Todo: TodoView(toAbout: root.navigateToAbout, componentWrapper: TodoComponentWrapper(component: child.component)).transition(.slide)
            case _ as RootComponentChild.About: AboutView(onBack: root.navigateBackFromAbout).transition(.slide)
            default: EmptyView()
            }
        }
        .frame(maxWidth:.infinity, maxHeight: .infinity)
        .animation(.easeIn(duration: 0.3), value: activeChild.hashValue)
    }
}

struct MaterialAppBar<Navigation : View, Action : View> : ViewModifier {
    private let title: String
    @ViewBuilder private let navigation : (() -> Navigation)?
    @ViewBuilder private let action : (() -> Action)?
    
    init(
        title: String,
        navigation: (() -> Navigation)?,
        action: (() -> Action)?
    ) {
        self.title = title
        self.navigation = navigation
        self.action = action
    }
    
    init(title: String) where Navigation == EmptyView, Action == EmptyView {
        self.init(title: title, navigation: nil, action: nil)
    }
    
    init(title: String, @ViewBuilder navigation: @escaping () -> Navigation) where Action == EmptyView {
        self.init(title: title, navigation: navigation, action: nil)
    }
    
    init(title: String, @ViewBuilder action: @escaping () -> Action) where Navigation == EmptyView {
        self.init(title: title, navigation: nil, action: action)
    }
    
    func body(content: Content) -> some View {
        VStack {
            HStack(alignment: .center) {
                navigation?().padding([.leading, .trailing], 10)
                
                if (navigation == nil) {
                    Spacer().frame(width: 16.0)
                }
                
                Text(title)
                    .font(.headline)
                    .lineLimit(2)
                
                Spacer()
                
                if (action == nil) {
                    Spacer().frame(width: 16.0)
                }
                
                action?().padding([.leading, .trailing], 10)
            }
            .padding([.vertical], 10)
            .border(.background)
            .shadow(radius: 1)
            
            content
        }
    }
}

extension View {
    func appBar(title: String) -> some View {
        modifier(MaterialAppBar(title: title))
    }
    
    func appBar<Navigation : View>(title: String, @ViewBuilder navigation: @escaping () -> Navigation) -> some View {
        modifier(MaterialAppBar(title: title, navigation: navigation))
    }
    
    func appBar<Action : View>(title: String, @ViewBuilder action: @escaping () -> Action) -> some View {
        modifier(MaterialAppBar(title: title, action: action))
    }
    
    func appBar<Navigation: View, Action: View>(title: String, @ViewBuilder navigation: @escaping () -> Navigation, @ViewBuilder action : @escaping () -> Action) -> some View {
        modifier(MaterialAppBar(title: title, navigation: navigation, action: action))
    }
}
