# Coding Assessment II

## Use cases
![use-cases-1](https://user-images.githubusercontent.com/20500388/158007467-7f7f7aff-5786-4d5d-9695-99db457c2919.png)
![use-cases-2](https://user-images.githubusercontent.com/20500388/158007469-fdfba88b-51c0-44d9-b7be-31bb3d23f5d3.png)

## Entity overview
![entity-overview](https://user-images.githubusercontent.com/20500388/158016750-37056c3c-fc70-4e96-ac18-2b97d96f4ee5.png)


## API Design

### For the APIs we should have resources by following.
1. Auth
2. TruckLocationPaths
3. Shipments
4. Assets
5. Dashboard
6. CurrencyRates
7. CashAdjustmentBatches



### APIs
| Method | Url                            | Description                                              | Go to reference                      | 
|--------|--------------------------------|----------------------------------------------------------|--------------------------------------|
| POST   | /api/auth/signin               | Log in                                                   | [Click](#post-auth)                  |
| POST   | /api/TruckLocationPaths        | Save a truck location path for IoT devices               | [Click](#post-truck-location-paths)  |
| POST   | /api/Shipments                 | Create new shipment                                      | [Click](#post-shipments)             |
| GET    | /api/Shipments?created={date}  | Get shipment by created date                             | [Click](#get-shipments)              |
| PUT    | /api/Shipments/{id}/status     | Update shipment status                                   | [Click](#put-shipment-status)        |
| POST   | /api/Assets                    | Create new asset (QR Code generator)                     | [Click](#post-assets)                |
| GET    | /api/Assets?shipmentId={id}    | List assets (for viewing as list)                        | [Click](#get-assets-listing)         |
| GET    | /api/Assets/{id}               | Get a asset (QR Code reader)                             | [Click](#get-assets-single)          |
| PUT    | /api/Assets/{id}               | Update asset                                             | [Click](#put-assets)                 |
| GET    | /api/Dashboard?shipmentId={id} | Display dashboard for tracking the shipment              | [Click](#get-dashboard)              |
| POST   | /api/CurrencyRates             | Receive currency rates from FXHub via webhook            | [Click](#post-currency-rates)        |
| POST   | /api/CashAdjustmentBatches     | Create batch file as csv and send to RAE system via SFTP | [Click](#post-cash-adjustment-batches) |



## User login

   First of all we have to login to identify the user. then we can know who is using the system.

### <a name="post-auth">POST /api/auth/signin</a>

Body

```json
{
   "username": "test",
   "password": "test"
}
```

Response

```json
{
   "expiredDate": "2022-03-01 22:33",
   "token": "ABC"
}
```

## Update truck location paths

### <a name="post-truck-location-paths">POST /api/TruckLocationPaths</a>

Body

```json
{
   "deviceId": "248494ef-3fee-4905-a33f-08bf10c3215c",
   "latitude": "13.7563",
   "longitude": "100.5018"
}
```

## Create new shipment

### <a name="post-shipments">POST /api/Shipments</a>

Body
```json
{
   "branchId": "c8cd6853-655b-4b21-8edd-f7f057ee1188",
   "cashCenterId": "bd840288-034d-4c68-a46d-b809705c413c",
   "driverId": "6f84ad6b-883b-4f8c-bbaa-a87751216e61",
   "truckId": "f651735b-a100-43bc-bdc2-44dd670a3af2",
   "deviceId": "248494ef-3fee-4905-a33f-08bf10c3215c",
   "origin": "BRANCH | CASH_CENTER",
   "destination": "BRANCH | CASH_CENTER"
}
```

Response
```json
{
   "id": "58515770-95c8-4c08-ab09-317f2bbf171a"
}
```

## Get shipment listing

### <a name="get-shipments">GET /api/Shipments?created={date}</a>

Response

```json
{
   "result": [
      {
         "id": "f84775c9-5c5a-494a-ad8d-12223ec67e34",
         "created": "2022-03-12 15:00:00",
         "createdBy": "Hero",
         "updated": "2022-03-12 15:00:00",
         "updatedBy": "Hero"
      }
   ]
}
```
## Update shipment status

### <a name="put-shipment-status">GET /api/Shipments/{id}/status</a>

Body

```json
{
    "status": "PREPARE | SHIPPING | DONE"
}
```

## Create new asset

### <a name="post-assets">POST /api/Assets</a>

Body
```json
{
   "shipmentId": "58515770-95c8-4c08-ab09-317f2bbf171a",
   "amount": 100,
   "currency": "THB"
}
```

Response
```json
{
   "id": "11e368a9-a777-413f-8b35-4f1497ab4d7e"
}
```

## Get asset listing

### <a name="get-assets-listing">GET /api/Assets?shipmentId={id}</a>

Response
```json
{
   "result": [
      {
         "id": "c3e3f4c9-5f36-4d22-91ef-263b2ebfeac7",
         "amount": 100,
         "currency": "THB"
      },
      {
         "id": "2860a4da-93b3-4102-ba45-7bed1fa301f5",
         "amount": 120,
         "currency": "THB"
      }
   ]
}
```

## Get asset by id
### <a name="get-assets-single">GET /api/Assets/{id}</a>

Response

```json
{
   "id": "c3e3f4c9-5f36-4d22-91ef-263b2ebfeac7",
   "amount": 100,
   "currency": "THB"
}
```
## Update asset

### <a name="put-assets">PUT /api/Assets/{id}</a>

Body

 ```json
{
   "amount": 200,
   "currency": "THB"
}
 ```

## Dashboard

### <a name="get-dashboard">GET /api/Dashboard?shipmentId={id}</a>

Response

```json
 {
    "truckLocation": {
       "latitude": "13.7563",
       "longitude": "100.5018"
    },
   "destinationLocation": {
      "latitude": "13.7563",
      "longitude": "100.5018"
   },
   "totalAmount": 1000
 }
```


## Update currency rates from FXHub via webhook

### <a name="post-currency-rates">POST /api/CurrencyRates</a>

Body

```json
 {
   "rates": [
      {
         "code": "USD",
         "rate": 0.030
      },
      {
         "code": "JPY",
         "rate": 3.52
      }
   ]
 }
```

## Send batch file to RAE system via SFTP

### <a name="post-cash-adjustment-batches">POST /api/CashAdjustmentBatches</a>

Body

```json
 {
    "balanceDate": "2022-03-12"
 }
```

