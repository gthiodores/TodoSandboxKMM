//
//  TodoItemView.swift
//  iosApp
//
//  Created by Lucy on 02/05/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TodoItemView: View {
    let todo: TodoItem
    
    var body: some View {
        VStack(alignment:.leading, spacing : 6.0) {
            Text(todo.title).font(.caption)
            Text(todo.content).font(.title2)
            Text(todo.uuid.uuidString).font(.callout)
        }
    }
}

struct TodoItemView_Previews: PreviewProvider {
    static var previews: some View {
        TodoItemView(
            todo: TodoItem(
                uuid: KmmUUID(),
                title: "test",
                content: "123",
                colorCode: ColorCode.white,
                tags: []
            )
        )
    }
}
