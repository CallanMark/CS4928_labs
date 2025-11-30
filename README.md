## Group Members : 
Mark callan (22363246) 
Jason Cushen (22342516)

## Useful commands 
- Compile: mvn -q -DskipTests compile
- Run: java -cp "target/classes" com.cafepos.demo.EntryPoint
- Test : mvn test

## Part D — Trade-offs: Layering vs Partitioning

(A) We believe that implementing a layered Monolith structure is more suitable than partitioning the system into multiple independent services for microservice architecture and was the best choice as for a project of the size and scale as we want to keep the architecture simple and avoid adding unnecessary complexity. Say if we had requirements that our POS system had to support 50,000 concurrent users than microservice architecture would be the obvious choice. The layered approach currently implemented in this project still keeps a clear separation of concerns. 

(B) Natural candidates for future partitioning would be Payments , Notifications , Inventory and Loyalty discount services. These services all have different scaling requirements i.e. Payments may need to handle up to 5-10x the requests that Loyalty would , The separation of these services would allow us to allocate varying and suitable amounts of hardware resources to these services. This would also isolate any faults in our system and through the independant deployment. Furthermore using a Kubernetes orchestration engine we could include automatic failover to replicas to make our application highly available and not suffer any outage when any service in our system fails  

(C) If the system was split into seperate services , We think to best way to handle inter-service communication would be through a combination of REST API endpoints and domain events.The clear contracts and predictable responses of REST endpoints would be suitable for creating order,processing payments,etc... While domain events would be suitable for asynchronous tasks like sending notifications and updating  customers loyalty points due to its loose coupling and resilience to partial failures 