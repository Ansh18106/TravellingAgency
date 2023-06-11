// const mongoose = require("mongoose");
import mongoose from "mongoose";

const PassengerSchema = new mongoose.Schema({
  passengerNumber: Number,
  balance: Number,
  type: Number,
  name: String,
  activityIds: { type: [Number] },
});

const Passenger = mongoose.model("Passenger", PassengerSchema);
export default Passenger;
