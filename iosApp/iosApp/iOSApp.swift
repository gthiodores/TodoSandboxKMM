import SwiftUI
import shared

@main
struct iOSApp: App {
    init(){
        KoinHelperKt.doInitKoin()
    }
    
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate
    
    @Environment(\.scenePhase)
    var scenePhase: ScenePhase
    
    var rootHolder : RootHolder { appDelegate.rootHolder }
    
    var body: some Scene {
        WindowGroup {
            RootView(rootHolder.root)
                .onChange(of: scenePhase) { newPhase in
                    switch newPhase {
                    case .background: LifecycleRegistryExtKt.stop(rootHolder.lifecycle)
                    case .inactive: LifecycleRegistryExtKt.pause(rootHolder.lifecycle)
                    case .active: LifecycleRegistryExtKt.resume(rootHolder.lifecycle)
                    default: break
                    }
                }
        }
    }
}
