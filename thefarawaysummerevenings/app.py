from flask import Flask, render_template, request
import os
import re
import random

from random import randint, choice
from nltk.util import ngrams
from functools import reduce
from flask import jsonify
import math

#Taken from su27's stackexchange post https://stackoverflow.com/questions/18967441/add-a-prefix-to-all-flask-routes/36033627#36033627
class PrefixMiddleware(object):

    def __init__(self, app, prefix=''):
        self.app = app
        self.prefix = prefix

    def __call__(self, environ, start_response):

        if environ['PATH_INFO'].startswith(self.prefix):
            environ['PATH_INFO'] = environ['PATH_INFO'][len(self.prefix):]
            environ['SCRIPT_NAME'] = self.prefix
            return self.app(environ, start_response)
        else:
            start_response('404', [('Content-Type', 'text/plain')])
            return ["This url does not belong to the app.".encode()]


app = Flask(__name__)
app.wsgi_app = PrefixMiddleware(app.wsgi_app, prefix='/thefaraway')

MAX_N = 5
SEED_MAX_SIZE = 20
MUTATION_CHANCE = 0.05
ADD_CHANCE = 0.03
LOSE_CHANCE = 0.03
POP_SIZE = 50
HINGE_P = .2
JOIN_P = .08
MIN_LENGTH = 10
MAX_LENGTH = 20

def read_text(txt_path=os.path.join(os.getcwd(), "texts", "WhateverItIsWhereverYouAre.txt")):
    with open(txt_path, 'r') as f:
        out = f.read()
    return out


def get_ngrams(txt, n=MAX_N):
    words = re.split(r"[ \t\n\r\f\v\.\!\?\",]+", txt.lower())
    return list_ngrams(words, n)


def list_ngrams(lst, n=MAX_N):
    the_ngrams = dict()
    the_ngrams[1] = lst
    for i in range(2, n + 1):
        the_ngrams[i] = set(ngrams(lst, i))

    return the_ngrams

txt = read_text()
ngs = get_ngrams(txt)


def n_sim(txta, n=MAX_N):
    a_ngrams = list_ngrams(txta)

    score = 0
    for i in range(2, n + 1):
        if len(a_ngrams[i]) >= 1:
            score += math.log1p(i) * len(a_ngrams[i].intersection(ngs[i])) / float(len(a_ngrams[i]))

    return score / (n - 1)


def weights(pop):
    return list(map(n_sim, pop))


def display_pop(pop):
    return "<br>".join(map(lambda l: " ".join(l), pop))


def mutate(t, mut_p = MUTATION_CHANCE, add_p = ADD_CHANCE, lose_p = LOSE_CHANCE, min_l = MIN_LENGTH, max_l = MAX_LENGTH):
    new = []
    words = ngs[1]
    for i, w in enumerate(t):
        r = random.random()
        if r <= mut_p:
            new.append(choice(words))
        elif r <= mut_p + add_p:
            new.append(w)
            new.append(choice(words))
        elif r <= mut_p + add_p + lose_p:
            pass
        else:
            new.append(w)
    if len(new) < min_l:
        for i in range(min_l - len(new)):
            new.append(choice(words))
    elif len(new) > max_l:
        new = new[:max_l]
    return new


def crossover(l, r):
    hingel = randint(0, len(l))
    hinger = randint(0, len(r))
    return l[:hingel] + r[hinger:]

def mutate_with_weight(x, mutp=MUTATION_CHANCE, addp=ADD_CHANCE, losep=LOSE_CHANCE):
    y = mutate(x, mut_p=mutp, add_p=addp, lose_p=losep)
    return [y, n_sim(y)]

def update(weighted_pop, mutp=MUTATION_CHANCE, addp=ADD_CHANCE, losep=LOSE_CHANCE):
    pop = [wp[0] for wp in weighted_pop]
    pop_weights = weights(pop)
    sample_l = random.choices(pop, pop_weights, k=POP_SIZE)
    sample_r = random.choices(pop, pop_weights, k=POP_SIZE)
    new_pop = []
    for i in range(POP_SIZE):
        new_pop.append(crossover(sample_l[i], sample_r[i]))
    return list(map(lambda x: mutate_with_weight(x, mutp, addp, losep), new_pop))


@app.route('/start/')
def genetictext():
    def sim_fit(t):
        return n_sim(t, ngs)

    def text_seed(max_len=SEED_MAX_SIZE):
        n = randint(MIN_LENGTH, SEED_MAX_SIZE)
        s = reduce(lambda a, b: a + [choice(ngs[1])], range(n + 1), [])
        return [s, n_sim(s)]

    pop = list(map(lambda x: text_seed(), range(POP_SIZE)))

    return jsonify(pop)


@app.route('/update/', methods=['POST'])
def a_generation():
    dat = request.get_json()
    new_pop = update(dat['population'], mutp=float(dat["mutp"]), addp=float(dat["addp"]), losep=float(dat["losep"]))
    return jsonify(new_pop)


@app.route('/')
def start_here():
    return render_template('environ.html')


if __name__ == '__main__':
    app.run(host='0.0.0.0')

