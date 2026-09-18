import SwiftUI
import Shared

@main
struct iOSApp: App {

    init() {
        let nativeSaver = ImageSaver()
        GallerySaverBridge.shared.initialize(delegate: nativeSaver)

        #if DEBUG
        LoggingKt.initKermit(isDebug: true)
        #else
        LoggingKt.initKermit(isDebug: false)
        #endif
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}