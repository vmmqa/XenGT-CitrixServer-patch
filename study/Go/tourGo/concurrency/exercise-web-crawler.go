package main

import (
    "fmt"
    "sync"
)

type Fetcher interface {
    // Fetch 返回 URL 的 body 内容，并且将在这个页面上找到的 URL 放到一个 slice 中。
    Fetch(url string) (body string, urls []string, err error)
}

var m map[string]int = make(map[string]int)
// Crawl 使用 fetcher 从某个 URL 开始递归的爬取页面，直到达到最大深度。
func Crawl(url string, depth int, safeFetcher Fetcher) {
    // TODO: 并行的抓取 URL。
    // TODO: 不重复抓取页面。
        // 下面并没有实现上面两种情况：
    if depth <= 0 {
        return
    }
    res, ok := fetcher[url]
    if ok == false {
        m[url] = 0
        return
    }
    fmt.Printf("found: %s, urls=%s\n", url, res.urls)
    m[url] = 1
    for _, u := range res.urls {
        _, ok := m[u]
        if ok!= false {
            continue
        }
        m[u] = 0
        Crawl(u, depth-1, fetcher)
    }
    return
}

func main() {
    Crawl("https://golang.org/", 4, fetcher)
    fmt.Println(m)
    for tmp, v := range m {
        fmt.Println(tmp,v)
        go Fetch(f,
    }
}


// fakeFetcher 是返回若干结果的 Fetcher。
type fakeFetcher map[string]*fakeResult

type fakeResult struct {
    body string
    urls []string
}

func (f fakeFetcher) Fetch(url string) (string, []string, error) {
    if res, ok := f[url]; ok {
        return res.body, res.urls, nil
    }
    return "", nil, fmt.Errorf("not found: %s", url)
}

// fetcher 是填充后的 fakeFetcher。
var fetcher = fakeFetcher{
    "https://golang.org/": &fakeResult{
         "The Go Programming Language",
        []string{
            "https://golang.org/pkg/",
            "https://golang.org/cmd/",
        },
    },
    "https://golang.org/pkg/": &fakeResult{
        "Packages",
        []string{
            "https://golang.org/",
            "https://golang.org/cmd/",
            "https://golang.org/pkg/fmt/",
            "https://golang.org/pkg/os/",
        },
    },
    "https://golang.org/pkg/fmt/": &fakeResult{
        "Package fmt",
        []string{
            "https://golang.org/",
            "https://golang.org/pkg/",
        },
    },
    "https://golang.org/pkg/os/": &fakeResult{
        "Package os",
        []string{
            "https://golang.org/",
            "https://golang.org/pkg/",
        },
    },
}

var mux sync.Mutex

