import sys
import scipy.signal.signaltools as sigtool
from scipy import signal
from scipy.signal import lfilter
import scipy.io.wavfile as wf
import numpy as np
import itertools
np.set_printoptions(threshold=sys.maxsize)

mark_f = 3125 ## 320 microseconds
space_f = 1562.5 ## 640 microseconds

def fsk_demodulator(fileName):

    # read the wave file

    fs, sig = wf.read(fileName)


    # Bandpass filter mark and space frequencies:

    center_freq = (mark_f + space_f)/2

    trans_width = 260    # Width of transition from pass band to stop band, Hz (assume for now.But must be calculated from Spectrum)

    numtaps = 10        # Size of the FIR filter.

    #1. Bandpass filter for mark frequency:

    band_1 = [center_freq, 2*mark_f - center_freq]  # Desired pass band, Hz

    edges_1 = [0, band_1[0] - trans_width, band_1[0], band_1[1],band_1[1] + trans_width, 0.5*fs]

    band_pass_1 = signal.remez(numtaps, edges_1, [0, 1, 0], Hz=fs)

    mark_filtered = signal.lfilter(band_pass_1, 1, sig)

    #2. Bandpass filter for space frequency:

    band_2 = [2*space_f - center_freq, center_freq]  # Desired pass band, Hz

    edges_2 = [0, band_2[0] - trans_width, band_2[0], band_2[1],band_2[1] + trans_width, 0.5*fs]

    band_pass_2 = signal.remez(numtaps, edges_2, [0, 1, 0], Hz=fs)

    space_filtered = signal.lfilter(band_pass_2, 1, sig)


    #3. Envelope detector on mark and space bandpass filtered signals:

    mark_env = np.abs(sigtool.hilbert(mark_filtered))
    space_env = np.abs(sigtool.hilbert(space_filtered))


    #4. Compare and decision:

    seq = range(0, len(mark_env))
    rx = []
    for i in seq:
        if (mark_env[i] > space_env[i]).any():
            rx.append(1)
        if (mark_env[i] < space_env[i]).any():
            rx.append(0)
        if (mark_env[i] == space_env[i]).any():
            rx.append(0)

    #5. Convert bit to byte:

    bytes = [sum([byte[b] << b for b in range(0,8)])
             for byte in zip(*(iter(rx),) * 8)
             ]

    #6. Converting bytearray to hexadecimal string

    hex = ', '.join(format(x, '02x') for x in bytes)

    return hex