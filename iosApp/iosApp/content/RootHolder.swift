//
//  RootHolder.swift
//  iosApp
//
//  Created by Lucy on 02/05/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

class RootHolder : ObservableObject {
    let lifecycle: LifecycleRegistry
    let root: RootComponent

    init(repostitory: TodoRepository) {
        lifecycle = LifecycleRegistryKt.LifecycleRegistry()

        root = DefaultRootComponent(
            componentContext: DefaultComponentContext(lifecycle: lifecycle),
            todoRepository: repostitory
        )

        LifecycleRegistryExtKt.create(lifecycle)
    }

    deinit {
        // Destroy the root component before it is deallocated
        LifecycleRegistryExtKt.destroy(lifecycle)
    }
}
