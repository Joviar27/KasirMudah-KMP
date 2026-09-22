# KasirMudah: Lightweight Transaction Bookkeeping Application

## Project Overview
KasirMudah is a lightweight bookkeeping application designed to digitize transaction management for small and medium-sized enterprises (SMEs). The application replaces manual paper-based ledger systems with a digital alternative, maintaining low system resource requirements for smooth performance on entry-level mobile devices.

This project is migrated from an existing native Android application, available in the original [KasirMudah Github Repository](https://github.com/Joviar27/KasirMudah).  
The user interface design specifications and wireframes for this version are documented in the [KasirMudah Figma Design File](https://www.figma.com/design/2ioaVuM4Kc1UJIsHvRxzOE/KasirMudah?node-id=0-1&t=aDCbHEFhY0hUqadr-1).

---

## Technical Stack
* **Kotlin Multiplatform (KMP) & Jetpack Compose Multiplatform:** Cross-platform development framework and declarative UI toolkit used to compile a shared codebase for Android and iOS.
* **Decompose:** A component-based architecture and navigation library for KMP that uses a tree-based component structure to isolate navigation, state management, and business logic from UI rendering.
* **Koin:** A dependency injection framework for Kotlin Multiplatform that uses a Domain Specific Language (DSL) to assemble application modules and manage dependencies seamlessly across layers.
* **Room KMP (Relational Database):** An abstraction layer over SQLite adapted for KMP, providing structured local persistence and relational data management across mobile platforms.
* **Jetpack DataStore Preferences:** A key-value storage solution used to persist lightweight user settings and configuration data asynchronously.
* **Coil 3 for KMP:** An image-loading library optimized for Kotlin Coroutines, used to fetch, cache, and render local and remote images.
* **Peekaboo:** A Kotlin Multiplatform library providing cross-platform image picking capabilities.

---

## System Architecture
The application implements **Clean Architecture** principles to enforce separation of concerns across three distinct layers:

* **Data Layer:** Handles data persistence and local storage abstractions, wrapping the Room database implementation.
* **Domain Layer:** Contains pure Kotlin core business rules, entity models, and input validation logic, independent of external frameworks.
* **Presentation Layer:** Manages user interface components, Decompose navigation nodes, rendering logic, and UI state synchronization.

---

## Functional Features

### Product Catalog Management
* **Product Registration:** Allows users to create new inventory entries with designated names and price values.
* **Item Removal:** Enables deletion of outdated or discontinued items from the store catalog.
* **Color Categorization:** Provides visual tags to group products or highlight specific categories.
* **Catalog Search:** Includes a search input field to query and filter catalog items by name.

![Product Catalog Feature Screenshot](https://github.com/user-attachments/assets/1a40bea1-0c9b-4465-a2b4-38d8f06663cf)

### Cart Operations
* **Cart Controls:** Supports item addition, quantity adjustments via increment/decrement actions, and one-tap cart resetting.
* **Receipt Generation & Confirmation:** Validates active cart items, generates a transactional receipt preview, and commits the transaction to local storage upon confirmation.

![Cart Operations Feature Screenshot](https://github.com/user-attachments/assets/2770e694-d9bb-410f-9aff-4a1a3c49ea19)

### Transaction History & Records
* **Transaction Ledger:** Displays an ordered history of completed sales, sorted chronologically with the most recent items first.
* **Range Filtering:** Allows users to filter transactions generated within certain time intervals.
* **Record Management:** Supports renaming transaction titles, deleting records, and bookmarking key transactions for quick access.
* **Receipt Export:** Enables exporting transaction receipts as images directly to the device photo library for archival or sharing purposes.

![Transaction Feature Screenshot](https://github.com/user-attachments/assets/170d6e07-662b-4af6-b07a-7ba39876e56e)
![Receipt Feature Screenshot](https://github.com/user-attachments/assets/33c84be4-8f01-4d14-8ab4-b40e9684f4c3)

### Store Profile Customization
* **Profile Configuration:** Allows users to modify the store name and upload or update the business profile image.
* **Receipt Personalization:** Dynamically applies the configured store name when generating new transaction receipts.

![Profile Feature Screenshot](https://github.com/user-attachments/assets/ae7d7ea6-7ccd-4c6a-8efc-db05d79248a8)
