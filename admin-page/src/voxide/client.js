import { VoxideClient } from "@voxide/react";

export const ai = new VoxideClient({
  publicKey: import.meta.env.VITE_VOXIDE_KEY
});