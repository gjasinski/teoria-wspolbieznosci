function printAsync(s, cb) {
    var delay = Math.floor((Math.random()*1000)+500);
    setTimeout(function() {
        console.log(s);
        if (cb) cb();
    }, delay);
}

function task1(cb, counter) {
    printAsync("1", function() {
        task2(cb, counter);
    });
}

function task2(cb, counter) {
    printAsync("2", function() {
        task3(cb, counter);
    });
}

function task3(cb, counter) {
    if(counter > 0) {
        printAsync("3", function() {
            task1(cb, --counter);
        });
    }
    else{
        printAsync("3", cb);
    }
}

// wywolanie sekwencji zadan

function loop(counter) {
    task1(function () {
        console.log('done!');
    }, counter);
}



/*
** Zadanie:
** Napisz funkcje loop(n), ktora powoduje wykonanie powyzszej
** sekwencji zadan n razy. Czyli: 1 2 3 1 2 3 1 2 3 ... done
**
*/

loop(4);
