//
//  TodoView.swift
//  iosApp
//
//  Created by Lucy on 02/05/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TodoView: View {
    let toAbout : () -> Void
    @StateObject var componentWrapper : TodoComponentWrapper
    
    var body: some View {
        NavigationView {
            List {
                ForEach(componentWrapper.uiState.items, id: \.uuid.uuidString) { item in
                    TodoItemView(todo: item)
                }
            }
            .navigationTitle("Todo")
            .toolbar {
                ToolbarItemGroup(placement: .navigationBarTrailing) {
                    Button(action: toAbout) {
                        Text("About")
                    }
                }
            }
            .navigationViewStyle(.stack)
        }
        
    }
}
