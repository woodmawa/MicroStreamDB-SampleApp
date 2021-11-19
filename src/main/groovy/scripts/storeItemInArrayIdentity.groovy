package scripts

import com.softwood.Customer
import com.softwood.DataRoot

DataRoot root = new DataRoot(name: "my root")
Customer cust = new Customer(name: "HSBC")

root.customers << cust

assert root.customers[0] == cust