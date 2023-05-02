//
//  TodoComponentWrapper.swift
//  iosApp
//
//  Created by Lucy on 02/05/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import Combine
import KMPNativeCoroutinesCombine
import shared

@MainActor class TodoComponentWrapper : ObservableObject {
    let component : TodoComponent
    
    @Published var uiState : TodoState = TodoState(
        items: [],
        title: "",
        body: "",
        colorCode: ColorCode.white
    )
    
    init(component: TodoComponent) {
        self.component = component
        createPublisher(for: component.uiStateFlow)
            .receive(on: DispatchQueue.main)
            .eraseToAnyPublisher()
            .assertNoFailure()
            .assign(to: &$uiState)
    }
    
    func onTitleChanged(value: String) {
        component.onTitleUpdated(value: value)
    }
    
    func onBodyChanged(value: String) {
        component.onBodyUpdated(value: value)
    }
    
    func onColorChanged(value: ColorCode) {
        component.onColorUpdated(value: value)
    }
}
