import SwiftUI

@main
struct iOSApp: App {

    init() {
        let nativeSaver = ImageSaver()
        GallerySaverBridge.shared.initialize(delegate: nativeSaver)
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}