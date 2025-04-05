### Purpose of DTO
ProductRequest - Request DTO
ProductResponse - Response DTO

| Name             | Purpose                      | Direction         |
|------------------|-------------------------------|-------------------|
| `ProductRequest` | Accept input from client      | Client → Server   |
| `ProductResponse`| Send data to client           | Server → Client   |
| `ProductDTO`     | General DTO (can be used for both or internal use) | Depends |

You can split or combine them based on your design preferences and validation needs. Let me know if you'd like to automate the mapping or include nested objects.

### Recommended Approach
The most commonly used approach is to separate request and response DTOs.