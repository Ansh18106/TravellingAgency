// const mongoose = require("mongoose");
import mongoose from "mongoose";

const PassengerTypeSchema = new mongoose.Schema({
  id: Number,
  subscriptionType: String,
  discountPercent: Number,
});

const PassengerType = mongoose.model("PassengerType", PassengerTypeSchema);
export default PassengerType;
