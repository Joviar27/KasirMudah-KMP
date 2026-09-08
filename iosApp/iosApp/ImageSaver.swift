import UIKit
import Photos
import Shared

class ImageSaver: NSObject, IOSNativeImageSaver {
    func save(uiImage: UIImage, onResult: @escaping @Sendable (KotlinBoolean, (any Error)?) -> Void) {
        let saver = SingleImageSaver { success, error in
            onResult(KotlinBoolean(value: success), error as NSError?)
        }
        saver.save(uiImage)
    }
}

private class SingleImageSaver: NSObject {
    private let onComplete: (Bool, NSError?) -> Void

    init(onComplete: @escaping (Bool, NSError?) -> Void) {
        self.onComplete = onComplete
    }

    func save(_ image: UIImage) {
        UIImageWriteToSavedPhotosAlbum(
            image,
            self,
            #selector(saveCompleted(_:didFinishSavingWithError:contextInfo:)),
            nil
        )
    }

    @objc private func saveCompleted(
        _ image: UIImage,
        didFinishSavingWithError error: Error?,
        contextInfo: UnsafeRawPointer
    ) {
        let nsError = error as NSError?
        onComplete(error == nil, nsError)
    }
}