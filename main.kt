import java.util.Scanner

const val GOLD_TICKET_PRICE = 10
const val SILVER_TICKET_PRICE = 8
const val MAX_SEATS_ON_A_GOLD_ROOM = 60
const val SHOW_THE_SEATS_MENU = 1
const val BUY_A_TICKET_MENU = 2
const val STATISTICS_MENU = 3

var numberOfSeatsByRow = 0
var numberOfRows = 0
var chosenSeats = mutableListOf<MutableList<Int>>()
var currentIncome = 0
    
fun showTheSeats() {
    //i print the entire room with choosed seat B
    var roomString = "\nCinema:\n  "
    for (i in 1..numberOfSeatsByRow) {
        roomString += i.toString() + " "
    }
    roomString += "\n"
    for (row in 1..numberOfRows) {
        roomString += "$row "
        for (seatInTheRow in 1..numberOfSeatsByRow) {
            var isChosen = false
            for(chosenSeat in chosenSeats) {
                if(chosenSeat[0] == row && chosenSeat[1] == seatInTheRow){
                    isChosen = true
                    break
                }    
            }
            if (isChosen) {
                roomString += "B "    
            } else {
                roomString += "S "
            }
        }
        roomString += "\n"
    }
    println(roomString)
}

fun buyATicket(rowNumber: Int, seatNumber:Int) {
    var roomString = "\nTicket price: \$"
    if (numberOfRows * numberOfSeatsByRow < MAX_SEATS_ON_A_GOLD_ROOM || rowNumber <= numberOfRows / 2) {
        roomString += GOLD_TICKET_PRICE
        currentIncome += GOLD_TICKET_PRICE
    } else {
        roomString += SILVER_TICKET_PRICE
        currentIncome += SILVER_TICKET_PRICE
    }
    println(roomString)
    //registered chosen seat in array
    if(rowNumber > numberOfRows || seatNumber > numberOfSeatsByRow) {
        throw IndexOutOfBoundsException("Wrong input!")
    }
    if(chosenSeats.indexOf(mutableListOf(rowNumber, seatNumber)) != -1) {
        throw Exception("That ticket has already been purchased!")
    }
    chosenSeats.add(mutableListOf(rowNumber, seatNumber))
    showTheSeats()
}

fun main() {
    val scanner = Scanner(System.`in`)
    println("Enter the number of rows: ")
    numberOfRows = scanner.nextInt()
    println("Enter the number of seats in each row: ")
    numberOfSeatsByRow = scanner.nextInt()
    val totalSeats = numberOfSeatsByRow * numberOfRows
    var totalIncome = 0
    if (numberOfRows * numberOfSeatsByRow < MAX_SEATS_ON_A_GOLD_ROOM) {
        totalIncome = totalSeats * GOLD_TICKET_PRICE
    } else {
        if(numberOfRows % 2 == 0) {
            totalIncome += numberOfRows / 2 * numberOfSeatsByRow * (GOLD_TICKET_PRICE + SILVER_TICKET_PRICE)  
        } else {
            totalIncome += numberOfRows / 2 * numberOfSeatsByRow * GOLD_TICKET_PRICE + (numberOfRows / 2 + 1) * numberOfSeatsByRow * SILVER_TICKET_PRICE
        }
    }

    do {
        println("1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit")
        val choiceMenu = scanner.nextInt()
        when (choiceMenu) {
            SHOW_THE_SEATS_MENU -> showTheSeats()
            BUY_A_TICKET_MENU -> {
                do {
                    var tryToBuyATicket = true
                    println("Enter a row number: ")
                    val rowNumber = scanner.nextInt()
                    println("Enter a seat number in that row: ")
                    val seatNumber = scanner.nextInt()
                    try {
                        buyATicket(rowNumber, seatNumber) 
                        tryToBuyATicket = false
                    } catch (e: Exception) {
                        println(e.message)
                    }    
                } while ( tryToBuyATicket == true)
            }
            STATISTICS_MENU -> {
                val percentage = (100 * chosenSeats.size).toDouble() / totalSeats.toDouble()
                val formatPercentage = "%.2f".format(percentage)

                println("Number of purchased tickets: ${chosenSeats.size}")
                println("Percentage: $formatPercentage%")
                println("Current income: \$$currentIncome")
                println("Total income: \$$totalIncome")
            }
            
        }
    } while (choiceMenu != 0) 
}
