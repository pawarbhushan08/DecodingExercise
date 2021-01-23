# DecodingExercise

1. Decode the binary data encoded in the audio fi le in WAV format (contained in the ZIP archive together with these instructions)

2. The data is encoded using Audio Frequency Shift-Keying (AFSK) in its simplest form: 
a) A single bit is the waveform between two zero-crossings.
b) A one signal is a rectangle signal of t = 320 microseconds.
c) A zero signal is a rectangle signal of t = 640 microseconds.
d) The real-life data might no longer be an ideal rectangle, since it’s been stored on physical media (e.g. a tape drive).
  
3.The bit-stream that can be extracted from the decoded audio signal can be converted into bytes 
a) The signal starts with a lead tone of roughly 2.5 seconds (all 1-bits, or 0xff bytes), and ends with an end block of about 0.5 seconds (all 1-bits).
b) 11 bits are used to encode a single byte – 8 bits for the byte plus one start bit (valued 0) and two stop bits (valued 1).
c) The data is encoded with least-signifi cant bit first.
  
4. The byte-stream has the following form:
a) The first two bytes are 0x42 and 0x0
b) After that, the data is structured in 64 messages of 30 bytes each, with the 31st byte being the checksum of the 30 bytes before that (in total 1984 bytes = 64 * 31 bytes). The last byte before the end block is a 0x00 byte.
     
5. The checksums will help you detect whether or not your decoding works

6. The data in this real-life fi le will have no meaning to you, unless you can fi gure out the machine that created it, and this is nearly impossible (so don’t try).
