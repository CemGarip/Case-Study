#language: en
@API @PetStore @CRUD_Flow
Feature: Petstore API - CRUD Operations for Pet Endpoint
Scenario: 1.0 - Full CRUD Cycle for Pet ID 998877
    # 1. CREATE
Given Pet details are prepared with ID: "998877" and name: "TestDog"
When a POST request is sent to the "/pet" endpoint
Then the response status code should be 200

    # 2. READ (Verify creation)
Given the previously created Pet ID "998877" is available
When a GET request is sent to the "/pet/{petId}" endpoint
Then the response status code should be 200
And the pet name should be "TestDog"

    # 3. UPDATE
Given the existing Pet ID "998877" is available
And new name is prepared as "UpdatedTestDog"
When a PUT request is sent to the "/pet" endpoint
Then the response status code should be 200
And the updated pet name in the response should be "UpdatedTestDog"

    # 4. DELETE
Given the existing Pet ID "998877" is available
When a DELETE request is sent to the "/pet/{petId}" endpoint
Then the response status code should be 200
And the message should be "998877"

    # 5. VERIFY DELETE
Given the deleted Pet ID "998877" is available
When a GET request is sent to the "/pet/{petId}" endpoint
Then the response status code should be 404