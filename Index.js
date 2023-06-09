import mongoose from "mongoose";
import express from "express";

// data imports
import Destination from "./Models/Destination.js";
import Activity from "./Models/Activity.js";
import Passanger from "./Models/Passanger.js";
import Package from "./Models/Package.js";
import { passengersData } from "./java-quick-start-master/data/passengers.js";
import { activitiesData } from "./java-quick-start-master/data/activities.js";
import { destinationData } from "./java-quick-start-master/data/destination.js";
import { travelPackagesData } from "./java-quick-start-master/data/travelPackages.js";

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
    // Passanger.insertMany(passengersData);
    // Activity.insertMany(activitiesData);
    // Destination.insertMany(destinationData);
    // Package.insertMany(travelPackagesData);
  })

  .catch((err) => console.log("ERROR:::", err));
