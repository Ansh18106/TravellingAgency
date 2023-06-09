// const mongoose = require("mongoose");
import mongoose from "mongoose";

const ActivitySchema = new mongoose.Schema({
  id: Number,
  name: String,
  description: String,
  destinationId: Number,
  cost: Number,
  capacity: Number,
  passengersId: { type: [Number] },
});

const Activity = mongoose.model("Activity", ActivitySchema);
export default Activity;
