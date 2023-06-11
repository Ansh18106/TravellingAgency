import mongoose from "mongoose";
import express from "express";

// data imports
import Destination from "./Models/Destination.js";
import Activity from "./Models/Activity.js";
import Passanger from "./Models/Passanger.js";
import Package from "./Models/Package.js";
import PassengerType from "./Models/PassengerType.js";
import { passengersData } from "./data/dataInJs/passengers.js";
import { activitiesData } from "./data/dataInJs/activities.js";
import { destinationData } from "./data/dataInJs/destination.js";
import { travelPackagesData } from "./data/dataInJs/travelPackages.js";
import { passangerTypeData } from "./data/dataInJs/passengerType.js";

/* CONFIGURATION */
const app = express();
app.use((req, res, next) => {
  res.send("express!!!!!");
});

/* MONGOOSE SETUP */

mongoose.set("strictQuery", false);

const PORT = process.env.PORT || 5001;
const MONGO_URL = process.env.MONGO_URL;
mongoose
  .connect(
    "mongodb+srv://anshbansal18106:c9Dpy2kixtbr1MdB@cluster0.hnswyqe.mongodb.net/?retryWrites=true&w=majority",
    {
      useNewUrlParser: true,
      useUnifiedTopology: true,
    }
  )
  .then(() => {
    app.listen(5001, () => console.log(`Server Connected to PORT: ${PORT}`));
    /* ONLY ADD ONE TIME */
    Passanger.insertMany(passengersData);
    Activity.insertMany(activitiesData);
    Destination.insertMany(destinationData);
    Package.insertMany(travelPackagesData);
    PassengerType.insertMany(passangerTypeData);
  })

  .catch((err) => console.log("ERROR:::", err));
