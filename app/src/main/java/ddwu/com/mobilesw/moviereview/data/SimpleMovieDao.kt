package ddwu.com.mobilesw.moviereview.data

import ddwu.com.mobilesw.moviereview.R

class SimpleMovieDao {
    val movies = ArrayList<MovieDto>()

    init {
        movies.add(MovieDto(1, R.mipmap.movie01,"다만악에서구하소서", "2020", "홍원찬", "황정민, 이정재, 박정민", "범죄, 액션", "이 오빠 게이에요?"))
        movies.add(MovieDto(2, R.mipmap.movie02,"엘리멘탈", "2023", "피터 손", "레아 루이스, 마무두 아티", "애니메이션", "네 빛이 일렁일 때가 좋아"))
        movies.add(MovieDto(3, R.mipmap.movie03,"극한직업", "2019", "이병헌", "류승룡, 이하늬, 진선규", "코미디", "지금까지 이런 맛은 없었다"))
        movies.add(MovieDto(4, R.mipmap.movie04,"기생충", "2019", "봉준호", "송강호, 이선균, 조여정", "드라마", "제시카 외동딸 일리노이 시카고"))
        movies.add(MovieDto(5, R.mipmap.movie05,"인턴", "2015", "낸시 마이어스", "앤 해서웨이, 로버트 드 니로", "코미디", "Experience never gets old"))
    }
}
