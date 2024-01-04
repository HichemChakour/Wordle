from flask import Flask, request, jsonify
from gensim.models import KeyedVectors

app = Flask(__name__)

model_path = 'C:/Users/hchak/IdeaProjects/wordle/frWac_non_lem_no_postag_no_phrase_200_skip_cut100.txt'

model = KeyedVectors.load_word2vec_format(model_path, binary=False)

@app.route('/find_similar_word', methods=['GET'])
def find_similar_word_endpoint():
    word = request.args.get('word')
    similar_word = find_similar_word(word)
    return jsonify({"similar_word": similar_word})

@app.route('/word_similarity', methods=['GET'])
def word_similarity_endpoint():
    word1 = request.args.get('word1')
    word2 = request.args.get('word2')
    similarity_score = word_similarity(word1, word2)
    return jsonify({"similarity_score": similarity_score})

@app.route('/top_similar_words', methods=['GET'])
def top_similar_words_endpoint():
    word = request.args.get('word')
    top_n = int(request.args.get('top_n', 10))
    similar_words = top_similar_words(word, top_n)
    return jsonify({"similar_words": similar_words})


def find_similar_word(word):
    similar_word, _ = model.most_similar(word, topn=1)[0]
    return similar_word

def word_similarity(word1, word2):
    similarity_score = model.similarity(word1, word2)
    return similarity_score

def top_similar_words(word, top_n=10):
    similar_words = model.most_similar(word, topn=top_n)
    return similar_words

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)