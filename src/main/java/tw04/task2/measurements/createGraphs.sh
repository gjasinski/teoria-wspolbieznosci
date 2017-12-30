#!/bin/bash
CONST1="
set term png\n

set xlabel \"M [cells]\"\n
set ylabel \"time [ns]\"\n
set output "
for i in 10 100 1000;
do
    for j in 1000 10000 100000;
    do
        for PREFIX in "get" "put";
        do
            rm gnuplot.data
            NOTFAIR=${PREFIX}${i}"_"${j}".txt"
            FAIR=${PREFIX}${i}"_fair_"${j}".txt"
            TITLE_FAIR="fair P=K="${i}" M="${j}
            TITLE_NOT_FAIR="not "${TITLE_FAIR}
            FILENAME="\""${PREFIX}${i}"_"${j}".png\""
            PLOT="\nplot \""${NOTFAIR}"\" title \""${TITLE_NOT_FAIR}"\", \""${FAIR}"\" title \""${TITLE_FAIR}"\""
            echo -e ${CONST1}${FILENAME}${PLOT} >> gnuplot.data
            gnuplot -c gnuplot.data
        done
    done
done
#gnuplot -c gnuplot.data
