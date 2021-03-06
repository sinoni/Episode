package com.ssafy.model.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "episode")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Episode {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "episode_pk")
	private Integer episodePk;
	
	@Column(name = "episode_title", length = 30, nullable = false)
	private String episodeTitle;
	
	@Column(name = "episode_content", nullable = false, columnDefinition="TEXT")
	private String episodeContent;

	@CreatedDate
	@Column(name = "episode_created_at", nullable = false)
	private ZonedDateTime episodeCreatedAt = ZonedDateTime.now();
	
	@Column(name = "episode_writer", length = 100, nullable = true)
	private String episodeWriter;
	
	@Column(name = "episode_view", nullable = false)
	@ColumnDefault("0")
	private Long episodeView;

	// episode <-> novel
	@ManyToOne
	@JoinColumn(name = "novel_pk", nullable = false)
	private Novel novel;
	
	// episode <-> comment
	@OneToMany(mappedBy = "episode", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
	private List<Comment> comments = new ArrayList<>();

	// episode <-> like_episode >> 에피소드 좋아요
	// 이 에피소드를 좋아하는 멤버들
	@ManyToMany
	@JoinTable(
			name = "like_episode",
			joinColumns = @JoinColumn(name = "episode_pk"),
			inverseJoinColumns = @JoinColumn(name = "mem_pk") 
			)	
	private List<Member> likedMembers = new ArrayList<>();

	@Builder
	public Episode(Novel novel,Integer episodePk, String episodeTitle, String episodeContent, ZonedDateTime episodeCreatedAt, String episodeWriter, Long episodeView) {
		this.novel = novel;
		this.episodePk = episodePk;
		this.episodeTitle = episodeTitle;
		this.episodeContent = episodeContent;
		this.episodeCreatedAt = episodeCreatedAt;
		this.episodeWriter = episodeWriter;
		this.episodeView = episodeView;
	}

	public Episode update(String episodeTitle, String episodeContent, String episodeWriter) {
		this.episodeTitle = episodeTitle;
		this.episodeContent = episodeContent;
		this.episodeWriter = episodeWriter;
		return this;
	}


	public void updateView(){ this.episodeView += 1;}

	public void beforeDelete(){
		//댓글
		comments.forEach(comment -> comment.beforeDelete());

		//좋아요
		likedMembers.forEach(member -> member.getLikeEpisodes().remove(this));
		likedMembers = new ArrayList<>();
	}
}
