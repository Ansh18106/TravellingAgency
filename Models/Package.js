// const mongoose = require("mongoose");
import mongoose from "mongoose";

const PackageSchema = new mongoose.Schema({
  id: Number,
  name: String,
  // capacity: Number,
  destinationIds: { type: [Number] },
  passanger: { type: [Number] },
});

const Package = mongoose.model("Package", PackageSchema);
export default Package;
