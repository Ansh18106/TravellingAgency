import FileSystem from "fs";

var travelPackages = [];
var Destinations = [];
var Activities = [];
var Passenger = [];
var PassengerType = [];
var PassengerType = [
  {
    id: 1,
    subscriptionType: "Standard",
    discountPercent: 0,
  },
  {
    id: 2,
    subscriptionType: "Gold",
    discountPercent: 10,
  },
  {
    id: 3,
    subscriptionType: "Platinum",
    discountPercent: 100,
  },
];

function getRandomNumber(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

const names = [
  "Aarav",
  " Aarushi",
  " Abhay",
  " Abhilash",
  " Abhishek",
  " Aditi",
  " Aditya",
  " Advait",
  " Agastya",
  " Aishwarya",
  " Akash",
  " Akshara",
  " Akshay",
  " Alia",
  " Aman",
  " Amar",
  " Ameya",
  " Amrita",
  " Ananya",
  " Aniket",
  " Anisha",
  " Anjali",
  " Ankita",
  " Ankit",
  " Ansh",
  " Anushka",
  " Aparna",
  " Arjun",
  " Arnav",
  " Arpita",
  " Arun",
  " Arya",
  " Asha",
  " Ashish",
  " Ashok",
  " Asmita",
  " Atharva",
  " Avani",
  " Ayush",
  " Bhagyashree",
  " Bharat",
  " Bhavana",
  " Bhavesh",
  " Bhuvan",
  " Chaitanya",
  " Chandan",
  " Chandni",
  " Charu",
  " Deepak",
  " Dev",
  " Devika",
  " Dhruv",
  " Diksha",
  " Dipika",
  " Divya",
  " Ekta",
  " Farhan",
  " Gaurav",
  " Gayatri",
  " Geeta",
  " Gopal",
  " Harish",
  " Harsha",
  " Harshad",
  " Harshal",
  " Himanshu",
  " Isha",
  " Ishan",
  " Ishita",
  " Jaya",
  " Jyoti",
  " Kajal",
  " Kamal",
  " Karan",
  " Kavita",
  " Kirti",
  " Komal",
  " Kunal",
  " Lakshmi",
  " Madhav",
  " Madhavi",
  " Madhuri",
  " Mahesh",
  " Mala",
  " Manish",
  " Manoj",
  " Mansi",
  " Meera",
  " Megha",
  " Mohan",
  " Mohini",
  " Mona",
  " Mukesh",
  " Naina",
  " Naveen",
  " Neelam",
  " Neha",
  " Niharika",
  " Nikhil",
  " Nilima",
  " Nisha",
  " Nishant",
  " Pankaj",
  " Parul",
  " Prachi",
  " Pradeep",
  " Pragya",
  " Prakash",
  " Prasad",
  " Pratik",
  " Priya",
  " Rahul",
  " Raj",
  " Rajat",
  " Rajeev",
  " Rajesh",
  " Rajiv",
  " Rajni",
  " Rakesh",
  " Ramesh",
  " Rani",
  " Ranjit",
  " Rashmi",
  " Ravi",
  " Reena",
  " Rekha",
  " Renu",
  " Riddhi",
  " Rishi",
  " Rohan",
  " Ruchi",
  " Rupali",
  " Sachin",
  " Sagar",
  " Sahil",
  " Sakshi",
  " Sameer",
  " Samir",
  " Sandeep",
  " Sanjay",
  " Sanjana",
  " Santosh",
  " Sarika",
  " Sarita",
  " Satish",
  " Seema",
  " Shalini",
  " Shashank",
  " Shefali",
  " Shilpa",
  " Shiv",
  " Shiva",
  " Shobha",
  " Shreya",
  " Shrikant",
  " Shubham",
  " Siddharth",
  " Simran",
  " Smita",
  " Sneha",
  " Sonal",
  " Sonam",
  " Sonia",
  " Sonu",
  " Suman",
  " Sumit",
  " Sunita",
  " Sunil",
  " Suresh",
  " Sushant",
  " Sushma",
  " Swati",
  " Tanvi",
  " Tarun",
  " Tina",
  " Tushar",
  " Uday",
  " Ujjwal",
  " Vandana",
  " Varsha",
  " Veena",
  " Veer",
  " Vicky",
  " Vidya",
  " Vijay",
  " Vineet",
  " Vishal",
  " Vivek",
  " Yamini",
  " Yash",
  " Yogesh",
  " Zeenat",
];

const giveafterAddLen = (variable, dependent, len) => {
  var listOfActs = [];
  for (var i = 0; i < len; ++i) {
    var num = variable * 10 + i;
    listOfActs.push(num);
    if (dependent.length === 1000) dependent[num].destinationId = variable;
    else if (dependent.length === 100) dependent[num].packageId = variable;
    else dependent[num].activityIds.push(variable);
  }
  return listOfActs;
};

for (var i = 0; i < 10000; ++i) {
  const passenger = {
    passengerNumber: i,
    name: names[getRandomNumber(0, 192)],
    balance: getRandomNumber(1000, 10000),
    type: getRandomNumber(1, 3),
    activityIds: [],
  };
  Passenger.push(passenger);
}
const addPassenger = (id, dependent) => {
  const len = getRandomNumber(0, 4);
  const res = [];
  // console.log(dependent);
  for (var i = 0; i < len; ++i) {
    const passengerId = getRandomNumber(0, 9999);
    res.push(passengerId);
    dependent[passengerId].activityIds.push(id);
  }
  return res;
};

for (var i = 0; i < 1000; ++i) {
  const activity = {
    id: i,
    name: `Activity${i}`,
    description: `description${i}`,
    destinationId: Math.floor(i / 10),
    cost: getRandomNumber(10, 100) * 10,
    capacity: getRandomNumber(6, 10),
    passengersId: addPassenger(i, Passenger),
  };
  Activities.push(activity);
}

for (var i = 0; i < 100; ++i) {
  const destination = {
    id: i,
    name: `Destination${i}`,
    packageId: Math.floor(i / 10),
    activityIds: giveafterAddLen(i, Activities, 10),
  };
  Destinations.push(destination);
}

for (var i = 0; i < 10; ++i) {
  const _package = {
    id: i,
    name: `Package${i}`,
    // capacity: getRandomNumber(100, 1000),
    destinationIds: giveafterAddLen(i, Destinations, 10),
  };
  travelPackages.push(_package);
}

console.log("Passenger: ", Passenger.length);
console.log("Activities: ", Activities.length);
console.log("Destinations: ", Destinations.length);
console.log("travelPackages: ", travelPackages.length);
console.log("PassengerType: ", PassengerType.length);

console.log(Passenger[0]);
console.log(Activities[0]);
console.log(Destinations[0]);
console.log(travelPackages[0]);
console.log(PassengerType[0]);

FileSystem.writeFile("Activities.json", JSON.stringify(Activities), (error) => {
  if (error) throw error;
});
FileSystem.writeFile("Passenger.json", JSON.stringify(Passenger), (error) => {
  if (error) throw error;
});
FileSystem.writeFile(
  "travelPackages.json",
  JSON.stringify(travelPackages),
  (error) => {
    if (error) throw error;
  }
);
FileSystem.writeFile(
  "Destinations.json",
  JSON.stringify(Destinations),
  (error) => {
    if (error) throw error;
  }
);
FileSystem.writeFile(
  "PassengerType.json",
  JSON.stringify(PassengerType),
  (error) => {
    if (error) throw error;
  }
);
