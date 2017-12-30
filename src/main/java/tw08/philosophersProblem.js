
var Fork = function() {
    this.state = 0;
    return this;
};

var rand = function() {
    return Math.floor((Math.random() * 100) + 1);
};

Fork.prototype.acquire = function(cb, iter) {
    if(this.state == 0){
        //console.log("biorem: "+ iter + " " + forks.indexOf(this));
        this.state = 1;
        cb();
    }
    else{
        //console.log("bede czekal: "+ iter + " " + forks.indexOf(this));
        setTimeout(() => { this.acquire(cb, iter+1); }, iter * 2);
    }
    // zaimplementuj funkcje acquire, tak by korzystala z algorytmu BEB
    // (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
    // 1. przed pierwsza proba podniesienia widelca Filozof odczekuje 1ms
    // 2. gdy proba jest nieudana, zwieksza czas oczekiwania dwukrotnie
    //    i ponawia probe itd.
};

var acquireValet = function(cb, iter) {
    if(valet > 0){
        //console.log("biorem: "+ iter + " " + forks.indexOf(this));
        valet--;
        cb();
    }
    else{
        //console.log("bede czekal: "+ iter + " " + forks.indexOf(this));
        setTimeout(() => { acquireValet(cb, iter+1); }, iter * 2);
    }
    // zaimplementuj funkcje acquire, tak by korzystala z algorytmu BEB
    // (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
    // 1. przed pierwsza proba podniesienia widelca Filozof odczekuje 1ms
    // 2. gdy proba jest nieudana, zwieksza czas oczekiwania dwukrotnie
    //    i ponawia probe itd.
};

Fork.prototype.release = function() {
    this.state = 0;
};

var Philosopher = function(id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id+1) % forks.length;
    return this;
};

Philosopher.prototype.startNaive = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    setTimeout( function () {
        for (var i = 0; i < count; i++) {
            setTimeout(() => {
                forks[f1].acquire(function () {
                    console.log("philosopher " + id + "acquire " + f1)
                    setTimeout(() => {
                        forks[f2].acquire(function () {
                            console.log("philosopher " + id + " acquire " + f2)
                            console.log("philosopher eat " + id + " ");
                            forks[f1].release();
                            forks[f2].release();
                        }, 1);
                }, 1);
                }, rand());
        }, rand());
        }
    }, rand());
    // zaimplementuj rozwiazanie naiwne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
};

Philosopher.prototype.startAsym = function(count) {
    var forks = this.forks,
        id = this.id;
    var f1,f2;
    if(id % 2 == 0){
      f1 = this.f2;
      f2 = this.f1;
    }
    else{
      f1 = this.f1;
      f2 = this.f2;
    }
    setTimeout( function () {
            for (var i = 0; i < count; i++) {
                setTimeout(() => {
                    forks[f1].acquire(function () {
                        console.log("philosopher " + id + "acquire " + f1)
                        setTimeout(() => {
                            forks[f2].acquire(function () {
                                console.log("philosopher " + id + " acquire " + f2)
                                console.log("philosopher eat " + id + " ");
                                forks[f1].release();
                                forks[f2].release();
                            }, 1);
                    }, 1);
                }, rand());
            }, rand());
            }
        }, rand());
    // zaimplementuj rozwiazanie asymetryczne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
};

Philosopher.prototype.startConductor = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    setTimeout( function () {
        for (var i = 0; i < count; i++) {
            setTimeout(() => {
                acquireValet(function() {
                    setTimeout(() => {
                        forks[f1].acquire(function () {
                            console.log("philosopher " + id + "acquire " + f1)
                            setTimeout(() => {
                                forks[f2].acquire(function () {
                                    console.log("philosopher " + id + " acquire " + f2)
                                    console.log("philosopher eat " + id + " ");
                                    forks[f1].release();
                                    forks[f2].release();
                                    valet++;
                                }, 1);
                            }, 1)
                        }, rand());
                    }, rand());
                }, 1);
            }, rand());
        }
    }, rand());
    // zaimplementuj rozwiazanie z kelnerem
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
};


var N = 5;
var valet = 4;
var forks = [];
var philosophers = [];
for (var i = 0; i < N; i++) {
    forks.push(new Fork());
    forks[i].id = i;
}

for (var i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}

for (var i = 0; i < N; i++) {
    //philosophers[i].startNaive(100);
    //philosophers[i].startAsym(100);
    philosophers[i].startConductor(3);
}
