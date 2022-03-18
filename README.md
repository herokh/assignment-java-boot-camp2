# Coding Assessment II

## Use cases
![use-cases-1](https://user-images.githubusercontent.com/20500388/158007467-7f7f7aff-5786-4d5d-9695-99db457c2919.png)
![use-cases-2](https://user-images.githubusercontent.com/20500388/158007469-fdfba88b-51c0-44d9-b7be-31bb3d23f5d3.png)

## Entity overview
![entity-overview](https://user-images.githubusercontent.com/20500388/158946158-3d951034-1417-43de-87c1-78da6f48869d.png)


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
| GET    | /api/Shipments?insertedDate={insertedDate}  | Get shipment by created date                             | [Click](#get-shipments)              |
| GET    | /api/Shipments/{id}  | Get shipment by id                             | [Click](#get-shipments-single)              |
| PUT    | /api/ShipmentStatusHistories?shipmentId={id}&status={newStatus}     | Update shipment status                                   | [Click](#put-shipment-status)        |
| POST   | /api/Assets                    | Create new asset (QR Code generator)                     | [Click](#post-assets)                |
| GET    | /api/Assets?shipmentId={id}    | List assets (for viewing as list)                        | [Click](#get-assets-listing)         |
| GET    | /api/Assets/{id}               | Get a asset (QR Code reader)                             | [Click](#get-assets-single)          |
| PUT    | /api/Assets/{id}               | Update asset                                             | [Click](#put-assets)                 |
| GET    | /api/Dashboard?shipmentId={id} | Display dashboard for tracking the shipment              | [Click](#get-dashboard)              |
| POST   | /api/CurrencyRates             | Receive currency rates from FXHub via webhook            | [Click](#post-currency-rates)        |
| POST   | /api/AccountingReports     | Create batch file as csv and send to RAE system via SFTP | [Click](#post-accounting-reports) |



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
  "shipmentId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "latitude": "string",
  "longitude": "string"
}
```

## Create new shipment

### <a name="post-shipments">POST /api/Shipments</a>

Body
```json
{
  "senderId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "receiverId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "driverId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "truckId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "deviceId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "shipmentType": "Unknown"
}
```

Response
```json
{
   "id": "58515770-95c8-4c08-ab09-317f2bbf171a"
}
```

## Get shipment listing

### <a name="get-shipments">GET /api/Shipments?insertedDate={insertedDate}</a>

Response

```json
{
  "result": [
    {
      "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "senderId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "receiverId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "shipmentType": "Unknown",
      "inserted": "2022-03-18T05:53:16.362Z",
      "insertedBy": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    }
  ]
}
```

## Get shipment single

### <a name="get-shipments-single">GET /api/Shipments/{id}</a>

Response

```json
{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "truckId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "deviceId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "driverId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "senderId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "receiverId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "shipmentType": "Unknown",
  "originLat": "string",
  "originLng": "string",
  "destinationLat": "string",
  "destinationLng": "string",
  "status": "Unknown",
  "inserted": "2022-03-18T05:52:43.989Z",
  "insertedBy": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
}
```

## Update shipment status

### <a name="put-shipment-status">GET /api/ShipmentStatusHistories?shipmentId={id}&status={newStatus}</a>


## Create new asset

### <a name="post-assets">POST /api/Assets</a>

Body
```json
{
  "shipmentId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "amount": 0,
  "currency": "string"
}
```

Response
```json
3fa85f64-5717-4562-b3fc-2c963f66afa6
```

## Get asset listing

### <a name="get-assets-listing">GET /api/Assets?shipmentId={id}</a>

Response
```json
{
  "result": [
    {
      "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "amount": 0,
      "currency": "string"
    }
  ]
}
```

## Get asset by id
### <a name="get-assets-single">GET /api/Assets/{id}</a>

Response

```json
{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "amount": 0,
  "currency": "string"
}
```
## Update asset

### <a name="put-assets">PUT /api/Assets/{id}</a>

Body

 ```json
{
  "amount": 0,
  "currency": "string"
}
 ```

## Dashboard

### <a name="get-dashboard">GET /api/Dashboard?shipmentId={id}</a>

Response

```json
{
  "truckLatitude": "string",
  "truckLongitude": "string",
  "cashCenterLatitude": "string",
  "cashCenterLongitude": "string",
  "totalBalances": 0
}
```


## Update currency rates from FXHub via webhook

### <a name="post-currency-rates">POST /api/CurrencyRates</a>

Body

```json
{
  "rates": [
    {
      "code": "string",
      "rate": 0
    }
  ]
}
```

## Send batch file to RAE system via SFTP

### <a name="post-accounting-reports">POST /api/AccountingReports</a>

Body

```json
{
  "cancelled": true,
  "done": true
}
```

