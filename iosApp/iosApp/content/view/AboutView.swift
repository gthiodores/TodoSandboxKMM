//
//  AboutView.swift
//  iosApp
//
//  Created by Lucy on 02/05/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct AboutView: View {
    let onBack: () -> Void
    
    var body: some View {
        NavigationView {
            ZStack(alignment: .center) {
                Text("About app")
            }
            .navigationTitle("About")
            .toolbar {
                ToolbarItemGroup(placement: .navigationBarLeading) {
                    Button(action: onBack, label: { Text("Back") })
                }
            }
            .navigationViewStyle(.stack)
        }
    }
}

struct AboutView_Previews: PreviewProvider {
    static var previews: some View {
        AboutView(onBack: {})
    }
}
