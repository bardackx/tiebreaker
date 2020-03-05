# Tiebreaker
A super small java library for sorting and picking by multiple criteria focused on code readability.

## Examples

### Pick
Given a list of candidates pick one using the following criteria:
- prioritize female over male
- if tied pick the younger candidate
- if tied pick the citizen candidate
- if tied pick the one with the highest score
- if tied pick the one with the highest funds
- if tied pick any candidate

```java
Candidate candidate = new Tiebreaker<Candidate>()
        .prefer(e -> e.isFemale())
        .lowest(e -> e.getAge())
        .prefer(e -> e.isCitizen())
        .highest(e -> e.getScore())
        .highest(e -> e.getFunds())
        .pick(list);
```

### Sort
Given the same criteria of the pick example sort all the candidates
```java
List<Candidate> sortedCandidates = new Tiebreaker<Candidate>()
        .prefer(e -> e.isFemale())
        .lowest(e -> e.getAge())
        .prefer(e -> e.isCitizen())
        .highest(e -> e.getScore())
        .highest(e -> e.getFunds())
        .sort(list);
```

### Support for temporal types
In addition to the java primitives the library supports Calendar, Date, LocalDate, Instant, and other types from the java.time package
```java
Shipments prioritizedShipment = new Tiebreaker<Shipment>()
		.earliest(e -> e.getArrivalDate());
		.latest(e -> e.getOrderDate())
		.pick(shipments);
```

## Planned

### Keep best N elements
Similar to sort but with a filter
```java
tiebreaker.keep(list, 5)
```

### Drop best N elements
Like pick(Iterable,int) but it drops the best N elements instead of keeping them
```java
tiebreaker.drop(list, 5)
```
