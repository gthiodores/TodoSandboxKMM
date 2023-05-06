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
        VStack(alignment: .center) {
            Text("About app")
        }
        .frame(maxHeight: .infinity)
        .appBar(
            title: "About",
            navigation: { Button(action: onBack, label: { Text("Back") }) }
        )
    }
}

struct AboutView_Previews: PreviewProvider {
    static var previews: some View {
        AboutView(onBack: {})
    }
}
