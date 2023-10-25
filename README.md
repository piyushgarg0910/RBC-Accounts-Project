# RBC-Accounts-Project

## Architecture
This project follows an MVVM architecture. The implementation of the entire project is based on the recommended architecture by Google. The code is divided into Data Layer, Domain Layer, and UI Layer.
1. The data layer is only responsible for getting/managing the data (from library, database, network, etc.)
2. The domain layer captures data from the data layer and prepares it for the UI layer. If the UI layer requires data from multiple endpoints, it is the domain layer's responsibility to get the data and combine it into one entity
3. The UI layer controls the UI from viewModels using UiStates.

### Screenshots
![Screenshot_20231024_185053_RBC User Accounts](https://github.com/piyushgarg0910/RBC-Accounts-Project/assets/11358861/dbb73122-aaf8-455d-8f5d-46ff6be2ebad)
Account Summary page shows the list of accounts sorted and grouped by Account Type
#### Note that the full account number is not shown in the list. Only the last 4 digits are visible on this page

![Screenshot_20231024_184942_RBC User Accounts](https://github.com/piyushgarg0910/RBC-Accounts-Project/assets/11358861/4d9eb5fb-c75a-4ddf-87d5-ebd183d1026a)
The Account Details page shows all transactions grouped and sorted by date in descending arrangement.
#### Note that the transactions for the same date are grouped together.

![Screenshot_20231024_184951_RBC User Accounts](https://github.com/piyushgarg0910/RBC-Accounts-Project/assets/11358861/e2c810e0-7490-4aed-a182-2fa6d77486d8)
![Screenshot_20231024_184956_RBC User Accounts](https://github.com/piyushgarg0910/RBC-Accounts-Project/assets/11358861/043087ca-c035-4744-b1bb-759ddb0c5e6d)
The Account number on the details screen is partially hidden by default. Users can reveal it by clicking the icon to the right of the account number.

![Screenshot_20231024_185040_RBC User Accounts](https://github.com/piyushgarg0910/RBC-Accounts-Project/assets/11358861/137deaeb-d1ef-49b1-b561-3459564464e9)
The loading state is shown when the app is collecting and processing the data from the backend.

![Screenshot_20231024_185035_RBC User Accounts](https://github.com/piyushgarg0910/RBC-Accounts-Project/assets/11358861/fb802057-04c5-45ba-9cba-6fa4a97372eb)
![Screenshot_20231024_185024_RBC User Accounts](https://github.com/piyushgarg0910/RBC-Accounts-Project/assets/11358861/8063e763-f860-4246-812c-0da6c8857097)
Different error states are shown if there was an exception in getting data from the backend or if the returned list of transactions was empty.

### Data Layer
The data layer is responsible for fetching the data from the library.

#### Note: The data layer is implemented in the following packages in the code - result and repo.

### Domain Layer
This layer acts as the pipeline between the Data layer and the UI layer. This layer requests the data from the data layer and prepares it for consumption in the UI. Here the transactions and additional transactions for credit card is combined into a single sorted list.

#### Note: The domain layer is implemented in the following packages in the code - result and useCase.

### UI Layer
The UI layer is responsible for driving the views. The ViewModel is responsible for maintaining the **UiState**. It updates the UiState with the data from the domain layer as well as from the user events. The fragment and activity (View) react to the changes in this UiState.

#### Note: The UI layer is implemented in the following packages in the code - view, viewModel, utils, and model.

## Focus
The main focus of the project was on the Architecture. The code is designed in a way that it is maintainable and scalable.

## UI
For the UI, RBC brand colours were used in designing the app. 

## Libraries and Components
The project is implemented in **Kotlin** as well as **Java**. The following libraries and components were used in creating the project
- Hilt
- StateFlow
- ViewModel
- List Adapter and RecyclerView Adapter
- Coroutines
- RecyclerView Item Separators
