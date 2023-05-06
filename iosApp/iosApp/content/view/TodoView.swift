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
        List {
            ForEach(componentWrapper.uiState.items, id: \.uuid.uuidString) { item in
                TodoItemView(todo: item)
            }
        }
        .listStyle(.plain)
        .frame(maxHeight: .infinity)
        .appBar(
            title: "Todo List",
            action: {
                Button(
                    action: toAbout,
                    label: { Text("About") }
                )                
            }
        )
    }
}
