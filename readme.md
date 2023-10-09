Book My Show
```agsl
This is a movie booking application where user can book the movie ticket.
```
Problems:

    1. How to represent Auditorium seat in UI ?
    Sol: - I use 2D matrix to represent the seat structure

    2. How to handle concurrency part where two or more user try to book same seat at the same time?
    Sol. - This is solved by using isolation level serializable and soft locking in DB table.
